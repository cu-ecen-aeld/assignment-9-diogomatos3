FROM    ubuntu:20.04
ARG UNAME=defaultuser

ENV DEBIAN_FRONTEND=noninteractive

# Set root user
USER    root

# configure system language
RUN     apt-get update && apt-get install -y --no-install-recommends locales && \
        locale-gen en_US.UTF-8 && \
        update-locale LANG=en_US.UTF-8 && \
        rm -rf /var/lib/apt/lists/*
ENV     LANG=en_US.UTF-8 \
        LANGUAGE=en_US:en \
        LC_ALL=en_US.UTF-8

# Create local user
RUN     adduser --disabled-password --force-badname $UNAME && \
        passwd -d $UNAME && \
        usermod -aG sudo $UNAME;

# Install required packages, for Yocto
RUN     apt-get update && \
        apt-get install -y --no-install-recommends build-essential chrpath cpio debianutils diffstat file gawk gcc git iputils-ping libacl1 liblz4-tool locales python3 python3-git python3-jinja2 python3-pexpect python3-pip python3-subunit socat texinfo unzip wget xz-utils zstd && \
        apt-get clean && \
        rm -rf /var/lib/apt/lists/*

# Install required packages, for the assignment
RUN     apt-get update && \
        apt-get install -y --no-install-recommends sshpass netcat psmisc && \
        apt-get clean && \
        rm -rf /var/lib/apt/lists/*

# Install Docker dependencies
RUN     apt-get update && \
        apt-get install -y --no-install-recommends\
        apt-transport-https \
        ca-certificates \
        curl \
        gnupg2 \
        lsb-release \
        sudo && \
        curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - && \
        echo "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null && \
        apt-get update && \
        apt-get install -y --no-install-recommends docker-ce docker-ce-cli containerd.io docker.io && \
        apt-get clean && \
        rm -rf /var/lib/apt/lists/*

# Create Docker group (if it doesn't exist)
RUN     groupadd docker || true

# Add user to Docker group
RUN     usermod -aG docker $UNAME

# Set user
USER    $UNAME

CMD     ["/bin/bash"]
