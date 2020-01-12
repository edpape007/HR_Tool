pipeline {
    agent any
    tools {
        maven 'Local Maven'
    }

    stages {
        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Deployment') {
            steps {
                bat 'echo "Shuttingdown app..."'
                bat 'mvn spring-boot:stop'
                bat 'echo "Starting app..."'
                bat 'mvn spring-boot:start'
            }
        }
    }
}