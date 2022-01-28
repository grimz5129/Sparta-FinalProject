#!/usr/bin/env bash

sudo apt-get update
sudo apt-get install nginx -y
sudo mv /home/ubuntu/default /etc/nginx/sites-available/default
sudo systemctl restart nginx && echo ubuntu
