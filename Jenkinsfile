@Library('my-shared-library') _
pipeline {
    agent any

    environment {
        IMAGE_NAME = "manishbadgujar/login-app"
        CONTAINER_NAME = "login-app-container"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                mavenCompile()
            }
        }

        stage('Test') {
            steps {
                mavenTest()
            }
        }

        stage('Build Maven') {
            steps {
                mavenPackage()
            }
        }

        stage('Remove Old Container') {
            steps {
                sh '''
                docker rm -f ${CONTAINER_NAME} || true
                '''
            }
        }

        stage('Remove Old Image') {
            steps {
                sh '''
                docker rmi -f ${IMAGE_NAME}:latest || true
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                docker build -t ${IMAGE_NAME}:latest .
                '''
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds',
                                                 usernameVariable: 'DOCKER_USER',
                                                 passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    '''
                }
            }
        }

        stage('Push Image to DockerHub') {
            steps {
                sh '''
                docker push ${IMAGE_NAME}:latest
                '''
            }
        }

        stage('Run Container') {
            steps {
                sh '''
                docker run -d -p 9090:8080 --name ${CONTAINER_NAME} ${IMAGE_NAME}:latest
                '''
            }
        }
    }
}

