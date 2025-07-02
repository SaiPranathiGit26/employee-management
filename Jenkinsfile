pipeline {
    agent any

    stages {
        stage('Pull Code') {
            steps {
                git url: 'https://github.com/SaiPranathiGit26/employee-management.git', branch: 'main'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t employee-app .'
            }
        }

        stage('Run with Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }
    }
}
