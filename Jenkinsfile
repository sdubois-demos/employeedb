// --------------------------------------------------------------------------------------------                                  
// File: ........: Jenkinsfile 
// GIT Repository: https://github.com/sdubois-tapdemo/employee-demo.git
// Location .....: employee-demo
// Author .......: Sacha Dubois, Fortinet                                                                                          
// Description ..: Build and Deploy the Newsletter Application to Kubernetes                                                                      
// --------------------------------------------------------------------------------------------   
pipeline {
    agent { node { label 'buildhost' } }
    
    environment {
        DEPLOYMENT="integration"

        // Extract MYSQL Credentials from Jenkins 
        MYSQL_INTEGRATION_USER     = credentials('mysql-integration-user')
        MYSQL_INTEGRATION_PASSWORD = credentials('mysql-integration-password')
        
        // Define Harbor Registry Credentials
        TDH_HARBOR_REGISTRY_DNS_HARBOR      =  "harbor.apps.corelab.core-software.ch"
        TDH_HARBOR_REGISTRY_ADMIN_USER      =  credentials('harbor-registry-user')
        TDH_HARBOR_REGISTRY_ADMIN_PASSWORD  =  credentials('harbor-registry-password')
    }
    
    stages {
        stage('Build') {
            steps {
                // Run Maven on a Unix agent.
                sh('mvn -Dmaven.test.failure.ignore=true \
                    -Dspring.profiles.active=integration \
                    -Dspring.datasource.username=$MYSQL_INTEGRATION_USER \
                    -Dspring.datasource.password=$MYSQL_INTEGRATION_PASSWORD \
                    clean package')
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit 'target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage ('Build Docker Image') {
            steps {
                script {
                    def pom = readMavenPom file: "pom.xml"
                    env.ARTIFACT_ID = pom.artifactId
                    env.ARTIFACT_VERSION = pom.version
                }
                
                sh('docker build -f Dockerfile \
                    --build-arg JAR_FILE=target/${ARTIFACT_ID}-${ARTIFACT_VERSION}.jar \
                    --build-arg IMAGE_VERSION=${ARTIFACT_VERSION} \
                    -t ${ARTIFACT_ID}:${ARTIFACT_VERSION} .')
            }
        }
        stage ('Push Docker Image to Harbor Registry') {
            steps {
                // Login to the Harbir container Registry
                sh('docker login $TDH_HARBOR_REGISTRY_DNS_HARBOR \
                    -u $TDH_HARBOR_REGISTRY_ADMIN_USER \
                    -p $TDH_HARBOR_REGISTRY_ADMIN_PASSWORD')
                    
                // TAG Docker Images for the Harbor Tegistry and upload the image
                sh('docker tag ${ARTIFACT_ID}:${ARTIFACT_VERSION} ${TDH_HARBOR_REGISTRY_DNS_HARBOR}/library/${ARTIFACT_ID}:latest')
                sh('docker push ${TDH_HARBOR_REGISTRY_DNS_HARBOR}/library/${ARTIFACT_ID}:latest')

                // Cleanup Buildfiles
                sh('docker builder prune -f')
            }
        }
    }
}
