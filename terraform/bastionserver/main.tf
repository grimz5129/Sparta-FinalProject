#################################
## Creating the bastion server ##
#################################

#Creating bastion subet
resource "aws_subnet" "java10x_userpackages_group4_subnet_bastion_tf" {
  vpc_id = var.var_vpc_id_tf
  cidr_block = "10.117.3.0/24"

  tags = {
    Name = "java10x_userpackages_group4_subnet_bastion"
  }
}

resource "aws_route_table_association" "java10x_userpackages_group4_rt_bastion_assoc_tf" {
  subnet_id = aws_subnet.java10x_userpackages_group4_subnet_bastion_tf.id
  route_table_id = var.var_rt_public_id_tf
}

resource "aws_network_acl" "java10x_userpackages_group4_nacl_bastion_tf" {
  vpc_id = var.var_vpc_id_tf

  #ingress means inbound
  ingress {
    protocol = "tcp"
    rule_no = 100
    action = "allow"
    cidr_block = "0.0.0.0/0"
    from_port = 22
    to_port = 22
  }

  ingress {
    protocol = "tcp"
    rule_no = 200
    action = "allow"
    cidr_block = "10.117.2.0/24"
    from_port = 1024
    to_port = 65535
  }

#egress means outbound
  egress {
    protocol = "tcp"
    rule_no = 100
    action = "allow"
    cidr_block = "10.117.2.0/24"
    from_port = 22
    to_port = 22
  }

  egress {
    protocol = "tcp"
    rule_no = 200
    action = "allow"
    cidr_block = "0.0.0.0/0"
    from_port = 1024
    to_port = 65535
  }

  #You can add more subnets connected to the same nacl, using commas to speperate
  subnet_ids = [aws_subnet.java10x_userpackages_group4_subnet_bastion_tf.id]

  tags = {
    Name = "java10x_userpackages_group4_nacl_bastion"
  }
}

#Creating a secuirty group
resource "aws_security_group" "java10x_userpackages_group4_sg_bastion_tf" {
  name = "java10x_userpackages_group4_sg_bastion"
  vpc_id = var.var_vpc_id_tf

  ingress {
    protocol = "tcp"
    from_port = 22
    to_port = 22
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    protocol = "tcp"
    from_port = 22
    to_port = 22
    cidr_blocks = ["10.117.2.0/24"]
  }

  egress {
    protocol = "tcp"
    from_port = 442
    to_port = 443
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    protocol = "tcp"
    from_port = 80
    to_port = 80
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "java10x_userpackages_group4_sg_bastion"
  }
}

#Creating aws instance
resource "aws_instance" "java10x_userpackages_group4_server_bastion_tf" {
  ami = var.var_ami_linux_ubuntu_tf
  instance_type = "t2.micro"
  key_name = "cyber-10x-group4"

  subnet_id = aws_subnet.java10x_userpackages_group4_subnet_bastion_tf.id
  vpc_security_group_ids = [aws_security_group.java10x_userpackages_group4_sg_bastion_tf.id]
  associate_public_ip_address = true

  tags = {
    Name = "java10x_userpackages_group4_server_bastion"
  }
}

resource "aws_route53_record" "java10x_userpackages_group4_r53_record_bastion_tf" {
    zone_id = var.var_zone_id_tf
    name = "bastion"
    type = "A"
    ttl = "30"

    records = [aws_instance.java10x_userpackages_group4_server_bastion_tf.public_ip]
}
