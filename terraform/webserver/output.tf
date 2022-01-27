output "output_userpackages_rt_public_id_tf" {
  value = aws_route_table.java10x_userpackages_group4_rt_public_tf.id
}

output "output_webserver_tf_id" {
  value = aws_instance.java10x_userpackages_group4_server_web_tf.*.id
}
