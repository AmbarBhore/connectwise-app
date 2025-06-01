pipeline {
      agent any
      environment {
     	  IMAGE_NAME = "rmm-agent"
          IMAGE_TAG = "3.1"
          DOCKER_REPO = "ambarbhore1234"
          K8S_DEPLOYMENT = "k8/s"
	  KUBECONFIG = credentials('k8s-jenkins-kubeconfig')
      }	

      stages {
	   stage('checkout code') {
	       steps {
		   echo 'checking out the source code'
		   checkout scm
	       }
           }
      	   stage('build with Maven') {
	       steps {
		    echo 'Building JAR with MAVEN ....'
		    sh 'mvn clean package'	
	       }
	   }
	   stage('Build docker image') {
	      steps {
		   echo "Building docker image ${DOCKER_REPO}/${IMAGE_NAME}:${IMAGE_TAG}"
		   sh "docker build -t ${DOCKER_REPO}/${IMAGE_NAME}:${IMAGE_TAG} ."
	      }
	   }
	   stage('Push image to docker hub') {
	      steps {
		   echo " pushing docker image to docker hub"
		   withCredentials([usernamePassword(credentialsId: 'dockerhub-cred', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
		   	sh """
			echo "$PASS" | docker login -u "$USER" --password-stdin
			docker push ${DOCKER_REPO}/${IMAGE_NAME}:${IMAGE_TAG}
			"""
		  }
	      }
	   } 
	   stage('Deploy to kubernetes') {
	       steps {
		   script {
		   	echo "Deploying appplication to the kubernetes"
			sh 'kubectl delete deployment rmm-agent'
		        sh 'kubectl apply -f k8s/deployment.yaml'
                        sh 'kubectl apply -f k8s/service.yaml'
			sh 'kubectl rollout restart deployment rmm-agent'
		        sh 'kubectl rollout status deployment/rmm-agent'
	       }
	   }
     }
   }
}
