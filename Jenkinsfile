pipeline {
      agent any
      environment {
     	  IMAGE_NAME = "rmm-agent"
          IMAGE_TAG = "3.1"
          DOCKER_REPO = "ambarbhore1234@gmail.com"
          K8S_DEPLOYMENT = "k8/s"
      }	

      stages {
	   stage('checkout code') {
	       steps {
		   echo 'checking out the source code'
		   checkout scm
	       }
           }
      }
}
