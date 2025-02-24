pipeline {
    agent any
    tools {
        maven 'Maven3' // Ensure Maven is installed and configured in Jenkins
    }
    triggers {
            pollSCM('H/1 * * * *') // Poll every 5 minutes
        }
    stages {
        stage('Checkout') {
            steps {
                // Checkout code from the Git repository using SSH
                git branch: 'main', credentialsId: 'ssh_bioaqua_api', url: 'git@github.com:abdoutalby/bioaqua_solution_api.git'
            }
        }

        stage('Build with Maven') {
            steps {
                // Run Maven build to create the Spring Boot JAR file
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build the Docker image for the application
                sh 'docker build -t spring-api:latest .'
            }
        }

        stage('Deploy Application') {
            steps {
                // Stop and remove any running container, then deploy the updated one
                sh '(docker stop spring-api && docker rm spring-api) || true'
                sh 'docker run -d  --network jenkins_bioaqua-network -p 9080:8080 --name spring-api  spring-api:latest'
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully. Application deployed.'
        }
        failure {
            echo 'Pipeline failed. Check the logs for details.'
        }
    }
}