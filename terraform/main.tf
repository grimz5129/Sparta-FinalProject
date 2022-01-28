###########################################
## Creating the vpc and shared resources ##
###########################################

provider "aws" {
  region = var.var_region_name_tf
}

#Creating the VPC
resource "aws_vpc" "java10x_userpackages_group4_vpc_tf"{
  cidr_block = "10.117.0.0/16"

  enable_dns_support = true
  enable_dns_hostnames = true

  tags = {
    Name = "java10x_userpackages_group4_vpc"
    }
}

resource "aws_route53_zone" "java10x_userpackages_group4_r53_zone_tf" {
    name = "group4.cyber"

    vpc {
      vpc_id = aws_vpc.java10x_userpackages_group4_vpc_tf.id
    }

    tags = {
      Name = "java10x_userpackages_group4_r53_zone"
    }
}

###################################
## Creating the webserver module ##
###################################

module "webserver_module" {
  source = "./webserver"

  var_ami_linux_ubuntu_tf = var.var_ami_linux_ubuntu_tf
  var_zone_id_tf = aws_route53_zone.java10x_userpackages_group4_r53_zone_tf.id
  var_private_key_location_tf = var.var_private_key_location_tf
  var_vpc_id_tf = aws_vpc.java10x_userpackages_group4_vpc_tf.id
  var_database_id = module.databaseserver_module.output_server_database_tf_id
}

##################################
## Creating the database module ##
##################################

module "databaseserver_module" {
  source = "./databaseserver"

  var_ami_linux_ubuntu_database_tf = var.var_ami_linux_ubuntu_database_tf
  var_zone_id_tf = aws_route53_zone.java10x_userpackages_group4_r53_zone_tf.id
  var_vpc_id_tf = aws_vpc.java10x_userpackages_group4_vpc_tf.id
}

#################################
## Creating the bastion module ##
#################################

module "bastionserver_module" {
  source = "./bastionserver"

  var_ami_linux_ubuntu_tf = var.var_ami_linux_ubuntu_tf
  var_zone_id_tf = aws_route53_zone.java10x_userpackages_group4_r53_zone_tf.id
  var_vpc_id_tf = aws_vpc.java10x_userpackages_group4_vpc_tf.id

  var_rt_public_id_tf = module.webserver_module.output_userpackages_rt_public_id_tf
}

###############################
## Creating the proxy module ##
###############################

module "proxyserver_module" {
  source = "./proxyserver"

  var_rt_public_id_tf = module.webserver_module.output_userpackages_rt_public_id_tf
  var_vpc_id_tf = aws_vpc.java10x_userpackages_group4_vpc_tf.id
  var_webserver_id_tf = module.webserver_module.output_webserver_tf_id
  var_ami_linux_ubuntu_tf = var.var_ami_linux_ubuntu_tf
  var_zone_id_tf = aws_route53_zone.java10x_userpackages_group4_r53_zone_tf.id
  var_private_key_location_tf = var.var_private_key_location_tf
}
