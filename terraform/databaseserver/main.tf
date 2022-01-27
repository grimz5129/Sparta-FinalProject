##################################
## Creating the database server ##
##################################

#Creating a subnet for the database
resource "aws_subnet" "java10x_userpackages_group4_subnet_private_tf" {
  vpc_id = var.var_vpc_id_tf
  cidr_block = "10.117.2.0/24"

  tags = {
    Name = "java10x_userpackages_group4_subnet_private"
  }
}

resource "aws_network_acl" "java10x_userpackages_group4_nacl_private_tf" {
  vpc_id = var.var_vpc_id_tf

  #ingress means inbound
  ingress {
    protocol = "tcp"
    rule_no = 100
    action = "allow"
    cidr_block = "10.117.1.0/24"
    from_port = 3306
    to_port = 3306
  }

  ingress {
    protocol = "tcp"
    rule_no = 200
    action = "allow"
    cidr_block = "10.117.3.0/24"
    from_port = 22
    to_port = 22
  }

#egress means outbound
  egress {
    protocol = "tcp"
    rule_no = 100
    action = "allow"
    cidr_block = "10.117.1.0/24"
    from_port = 1024
    to_port = 65535
  }

  egress {
    protocol = "tcp"
    rule_no = 200
    action = "allow"
    cidr_block = "10.117.3.0/24"
    from_port = 1024
    to_port = 65535
  }

  #You can add more subnets connected to the same nacl, using commas to speperate
  subnet_ids = [aws_subnet.java10x_userpackages_group4_subnet_private_tf.id]

  tags = {
    Name = "java10x_userpackages_group4_nacl_private"
  }
}

#Creating a secuirty group
resource "aws_security_group" "java10x_userpackages_group4_sg_database_tf" {
  name = "java10x_userpackages_group4_sg_database"
  vpc_id = var.var_vpc_id_tf

  ingress {
    protocol = "tcp"
    from_port = 3306
    to_port = 3306
    cidr_blocks = ["10.117.1.0/24"]
  }

  ingress {
    protocol = "tcp"
    from_port = 22
    to_port = 22
    cidr_blocks = ["10.117.3.0/24"]
  }

  tags = {
    Name = "java10x_userpackages_group4_sg_database"
  }
}

#Creating aws instance
resource "aws_instance" "java10x_userpackages_group4_server_database_tf" {
  ami = var.var_ami_linux_ubuntu_database_tf

  instance_type = "t2.micro"
  key_name = "cyber-10x-group4"

  subnet_id = aws_subnet.java10x_userpackages_group4_subnet_private_tf.id
  vpc_security_group_ids = [aws_security_group.java10x_userpackages_group4_sg_database_tf.id]
  associate_public_ip_address = false

  tags = {
    Name = "java10x_userpackages_group4_server_database"
  }
}

resource "aws_route53_record" "java10x_userpackages_group4_r53_record_database_tf" {
    zone_id = var.var_zone_id_tf
    name = "db"
    type = "A"
    ttl = "30"

    records = [aws_instance.java10x_userpackages_group4_server_database_tf.private_ip]
}
