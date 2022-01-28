pipeline {
  environment {
    PROJECT_DIR = "/app"
    CONTAINER_NAME = "finproject"
    DOCKER_ACCOUNT = "grimz5129"
    REGISTRY = "$DOCKER_ACCOUNT" + "/" + "$CONTAINER_NAME"
    IMAGE_NAME = "$REGISTRY" + ":" + "$BUILD_NUMBER"
    REGISTRY_CREDENTIALS = "docker_hub"
    DOCKER_IMAGE = ''
  }

  agent any

  options {
    skipStagesAfterUnstable()
  }

  stages {
    stage('Clone from Git') {
      steps {
        checkout scm
      }
    }

    stage('Build-Image') {
      steps{
        script {
          DOCKER_IMAGE = docker.build IMAGE_NAME
        }
      }
    }

    stage('Test') {
    	steps {
        script {
          sh '''
            docker run --rm -v $PWD/test-results:/app/target/surefire-reports --workdir $PROJECT_DIR --name $CONTAINER_NAME $IMAGE_NAME mvn test
          '''
        }
      }
    	post {
    			always {
    					junit testResults: '**/test-results/*.xml'
    			}
    	}
    }

    stage('Deploy Image') {
      steps{
        script {
          docker.withRegistry( '', REGISTRY_CREDENTIALS ) {
            DOCKER_IMAGE.push()
          }
        }
      }
    }

    stage('Remove Unused docker image') {
      steps{
        sh "docker rmi $IMAGE_NAME"
      }
    }
  }
}
