scp -i "./terraform/modules/ec2/app-key-pair" ./target/petstation-0.0.1-SNAPSHOT.jar ec2-user@ec2-34-210-253-95.us-west-2.compute.amazonaws.com:/home/ec2-user
ssh -i "./modules/ec2/app-key-pair" ec2-user@ec2-34-210-253-95.us-west-2.compute.amazonaws.com
sudo yum update -y
sudo amazon-linux-extras install java-openjdk17 -y
java -jar petstation-0.0.1-SNAPSHOT.jar
ssh -i "app-key-pair.pem"
