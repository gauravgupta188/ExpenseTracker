pipeline {
    agent any

    environment {
        FIREBASE_TOKEN       = credentials('FIREBASE_TOKEN')
        FIREBASE_APP_ID      = credentials('FIREBASE_APP_ID')
        FIREBASE_TESTER_GROUP = credentials('FIREBASE_TESTER_GROUP')
        ANDROID_HOME         = "/Users/kumargaurav/Library/Android/sdk"
        PATH                 = "$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$PATH"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'github-ssh-key',
                    url: 'https://github.com/gauravgupta188/ExpenseTracker.git'
            }
        }

        stage('Setup Ruby') {
            steps {
                sh '''
                    export PATH="$HOME/.rbenv/bin:$PATH"
                    eval "$(rbenv init - zsh)"
                    rbenv local 3.3.0
                    gem install bundler
                    bundle install
                '''
            }
        }

        stage('Run Fastlane') {
            steps {
                sh '''
                    export PATH="$HOME/.rbenv/bin:$PATH"
                    eval "$(rbenv init - zsh)"
                    bundle exec fastlane firebase_dist
                '''
            }
        }

    }

    post {
        success {
            echo '✅ Build succeeded! APK uploaded to Firebase App Distribution.'
        }
        failure {
            echo '❌ Build failed. Check the logs above.'
        }
    }
}
