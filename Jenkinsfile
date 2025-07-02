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
          dir('backend') {
            sh 'mvn clean install'
        }
    }
}

        stage('Run Unit Tests') {
    steps {
        dir('backend') {
            sh 'mvn test'
        }
    }
}
stage('Build Docker Image') {
    steps {
        dir('backend') {
            sh 'docker build -t employee-backend .'
        }
    }
}
stage('Run with Docker Compose') {
    steps {
        dir('backend') {
            sh 'docker-compose up -d'
        }
    }
}

    }
}
