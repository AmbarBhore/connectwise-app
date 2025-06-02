pipeline {
      agent any
      environment {
     	  IMAGE_NAME = "rmm-agent"
          IMAGE_TAG = "${BUILD_NUMBER}"
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
	   stage('Verify jar output') {
	       steps {
		     echo "list all JARs in target directory"
		     sh 'ls -lh target/*.jar'
			
		    echo "checking for Main-Class in manifest"
		    sh 'unzip -p target/rmm-agent-3.1.jar META-INF/MANIFEST.MF | grep Main-Class || true'
	      
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
		        sh 'kubectl apply -f k8s/deployment.yaml'
                        sh 'kubectl apply -f k8s/service.yaml'
			sh "kubectl set image deployment/rmm-agent rmm-agent=${DOCKER_REPO}/${IMAGE_NAME}:${IMAGE_TAG}"
		        sh 'kubectl rollout status deployment/rmm-agent'
	       }
	   }
     }
   }
}
