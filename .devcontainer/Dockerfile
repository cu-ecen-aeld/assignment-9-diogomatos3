FROM    ubuntu:20.04
ARG UNAME=defaultuser

ENV DEBIAN_FRONTEND=noninteractive \
    LANG=en_US.UTF-8 \
    LANGUAGE=en_US:en \
    LC_ALL=en_US.UTF-8

# Set root user
USER    root

# Configure system language and install common dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
    locales sudo curl gnupg2 apt-transport-https ca-certificates && \
    echo "en_US.UTF-8 UTF-8" > /etc/locale.gen && locale-gen && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Create local user
RUN adduser --disabled-password --force-badname $UNAME && \
    passwd -d $UNAME && \
    usermod -aG sudo $UNAME

# Install Yocto dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
    build-essential chrpath cpio debianutils diffstat file gawk gcc git \
    iputils-ping libacl1 liblz4-tool python3 python3-git python3-jinja2 \
    python3-pexpect python3-pip python3-subunit socat texinfo unzip wget \
    xz-utils zstd && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Install assignment-specific dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
    sshpass netcat psmisc openssh-client && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Install Docker
RUN curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add - && \
    echo "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable" > /etc/apt/sources.list.d/docker.list && \
    apt-get update && apt-get install -y --no-install-recommends \
    docker-ce docker-ce-cli containerd.io && \
    groupadd docker || true && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Copy the entrypoint script
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Set the entrypoint
ENTRYPOINT ["/entrypoint.sh"]

# Switch to non-root user
USER $UNAME

# Default command
CMD ["/bin/bash"]
