pipeline {
    agent { label 'backend-agent' }
    environment {
        NEXUS_REGISTRY = 'nexus:5000'   // Your private Nexus Docker registry URL (host:port)
        DOCKER_REPO = 'myrepo'                      // Nexus Docker repo name
        IMAGE_TAG = "${env.BUILD_NUMBER}"
        //DOCKER_CREDS_ID = 'nexus-docker-creds'           // Jenkins credential ID for Nexus Docker registry
        GIT_REPO_URL = 'https://github.com/PapatzelosThanashs/proj.git'
        GIT_BRANCH = 'master'
        //GIT_CREDENTIALS_ID = 'github-creds'      
    }

    stages {
     
           stage('Checkout') {
            steps {
                // Checkout GitHub repo (private or public)
                checkout([$class: 'GitSCM',
                    branches: [[name: "*/${GIT_BRANCH}"]],
                    userRemoteConfigs: [[
                        url: "${GIT_REPO_URL}",
                        //credentialsId: "${GIT_CREDENTIALS_ID}" // Remove if public repo
                    ]]
                ])
            }
        }
 
        stage('Build Frontend') {
            when {
                changeset "**/frontend/**"
            }
            steps {
                dir('frontend') {
                    script {
                        docker.build("${NEXUS_REGISTRY}/${DOCKER_REPO}/frontend:${IMAGE_TAG}")
                    }
                }
            }
        }

        stage('Build Backend') {
            when {
                changeset "**/demo/**"
            }
            steps {
                sh 'mvn clean package'
                dir('demo') {
                    script {
                        docker.build("${NEXUS_REGISTRY}/${DOCKER_REPO}/backend:${IMAGE_TAG}")
                    }
                }
            }
        }

        stage('Build DB') {
            when {
                changeset "**/db/**"
            }
            steps {
                dir('db') {
                    script {
                        docker.build("${NEXUS_REGISTRY}/${DOCKER_REPO}/db:${IMAGE_TAG}")
                    }
                }
            }
        }

        stage('Push Images') {
            steps {
                withDockerRegistry([credentialsId: "${DOCKER_CREDS_ID}", url: "http://${NEXUS_REGISTRY}"]) {
                    script {
                        if (fileExists('frontend/Dockerfile')) {
                            docker.image("${NEXUS_REGISTRY}/${DOCKER_REPO}/frontend:${IMAGE_TAG}").push()
                        }
                        if (fileExists('demo/Dockerfile')) {
                            docker.image("${NEXUS_REGISTRY}/${DOCKER_REPO}/backend:${IMAGE_TAG}").push()
                        }
                        if (fileExists('db/Dockerfile')) {
                            docker.image("${NEXUS_REGISTRY}/${DOCKER_REPO}/db:${IMAGE_TAG}").push()
                        }
                    }
                }
            }
        }

    }
}
