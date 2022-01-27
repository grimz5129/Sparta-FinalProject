###############################
## Creating the proxy server ##
###############################

#Creating a subent for the proxy server
resource "aws_subnet" "java10x_userpackages_group4_subnet_proxy_tf" {
  vpc_id = var.var_vpc_id_tf
  cidr_block = "10.117.4.0/24"

  tags = {
    Name = "java10x_userpackages_group4_subnet_proxy"
  }
}

#Creating a route table association
resource "aws_route_table_association" "java10x_userpackages_group4_rt_assoc_proxy_tf" {
  subnet_id = aws_subnet.java10x_userpackages_group4_subnet_proxy_tf.id
  route_table_id = var.var_rt_public_id_tf
}

#Creating a nacl
resource "aws_network_acl" "java10x_userpackages_group4_nacl_proxy_tf" {
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
    cidr_block = "0.0.0.0/0"
    from_port = 443
    to_port = 443
  }

  ingress {
    protocol = "tcp"
    rule_no = 300
    action = "allow"
    cidr_block = "0.0.0.0/0"
    from_port = 1024
    to_port = 65535
  }

  egress {
    protocol = "tcp"
    rule_no = 100
    action = "allow"
    cidr_block = "0.0.0.0/0"
    from_port = 443
    to_port = 443
  }

  egress {
    protocol = "tcp"
    rule_no = 200
    action = "allow"
    cidr_block = "0.0.0.0/0"
    from_port = 80
    to_port = 80
  }

  egress {
    protocol = "tcp"
    rule_no = 1000
    action = "allow"
    cidr_block = "0.0.0.0/0"
    from_port = 1024
    to_port = 65535
  }

  #You can add more subnets connected to the same nacl, using commas to speperate
  subnet_ids = [aws_subnet.java10x_userpackages_group4_subnet_proxy_tf.id]

  tags = {
    Name = "java10x_userpackages_group4_nacl_proxy"
  }
}

#Creating a secuirty group
resource "aws_security_group" "java10x_userpackages_group4_sg_proxy_tf" {
  name = "java10x_userpackages_group4_sg_proxy"
  vpc_id = var.var_vpc_id_tf

  ingress {
    protocol = "tcp"
    from_port = 22
    to_port = 22
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    protocol = "tcp"
    from_port = 443
    to_port = 443
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    protocol = "tcp"
    from_port = 443
    to_port = 443
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    protocol = "tcp"
    from_port = 8080
    to_port = 8080
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    protocol = "tcp"
    from_port = 80
    to_port = 80
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "java10x_userpackages_group4_sg_web"
  }
}

#Creating aws instance
resource "aws_instance" "java10x_userpackages_group4_server_proxy_tf" {
  ami = var.var_ami_linux_ubuntu_tf
  instance_type = "t2.micro"
  key_name = "cyber-10x-group4"

  subnet_id = aws_subnet.java10x_userpackages_group4_subnet_proxy_tf.id
  vpc_security_group_ids = [aws_security_group.java10x_userpackages_group4_sg_proxy_tf.id]
  associate_public_ip_address = true

  depends_on = [var.var_webserver_id_tf]

  #creating ssh connection
   connection {
     type = "ssh"
     host = self.public_ip
     user = "ubuntu"
     #private key location
     private_key = file(var.var_private_key_location_tf)
   }


   provisioner "remote-exec" {
     inline = [
       "ls",
     ]
   }

   provisioner "local-exec" {
     working_dir = "../ansible"
     environment = {
       ANSIBLE_CONFIG = "${abspath(path.root)}/../ansible"
     }
     command = "ansible-playbook -i ${self.public_ip}, -u ubuntu playbook-nginx.yml"
   }

  tags = {
    Name = "java10x_userpackages_group4_server_proxy"
  }
}

resource "aws_route53_record" "java10x_userpackages_group4_r53_record_proxy_tf" {
    zone_id = var.var_zone_id_tf
    name = "proxy"
    type = "A"
    ttl = "30"

    records = [aws_instance.java10x_userpackages_group4_server_proxy_tf.public_ip]
}
