from ubuntu:16.04

RUN apt-get autoclean
RUN apt-get clean

# Install OpenJDK-8 and maven
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y ant && \
    apt-get install -y maven && \
    apt-get clean;

# Fix certificate issues
RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME

RUN apt-get install wget -y
# Set the Chrome repo.
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list
# Install Chrome.
RUN apt-get update && apt-get -y install google-chrome-stable


# To run browser in headless mode
RUN apt-get install xvfb -y
RUN apt-get install libgconf-2-4 -y


# Set up Chromedriver Environment variables
ENV CHROMEDRIVER_VERSION 85.0.4183.87
ENV CHROMEDRIVER_DIR /chromedriver
RUN mkdir $CHROMEDRIVER_DIR

RUN apt-get install unzip -y


# Download and install Chromedriver
RUN wget -q --continue -P $CHROMEDRIVER_DIR "http://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_linux64.zip"
RUN unzip $CHROMEDRIVER_DIR/chromedriver* -d $CHROMEDRIVER_DIR
RUN chmod +x $CHROMEDRIVER_DIR/chromedriver

# Put Chromedriver into the PATH
ENV PATH /chromedriver:$PATH

COPY . /usr/src/app

WORKDIR /usr/src/app


ENTRYPOINT ["mvn", "clean", "install", "-Dtestng.dtd.http=true"]






