@Library('my-shared-library') _
pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

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
                removeContainer()
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
                pushContainer()
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

