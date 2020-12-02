FROM debian AS builder

ENV APPD_AGENT_VERSION="20.10.0.31173"
ENV APPD_AGENT_SHA256="5bab2ed2918190a33523434a60a76573391faf587751c41c9b8b434279d96f69"
ENV KTOR_VERSION="1.2"

RUN apt-get update && apt-get install -y --no-install-recommends  unzip \
	&& rm -rf /var/lib/apt/lists/*

COPY libs/AppServerAgent-1.8-${APPD_AGENT_VERSION}.zip target/NAV-SDK-KTOR-${KTOR_VERSION}.jar /
RUN echo "${APPD_AGENT_SHA256} *AppServerAgent-1.8-${APPD_AGENT_VERSION}.zip" >> appd_checksum \
    && sha256sum -c appd_checksum \
    && rm appd_checksum \
    && unzip -oq AppServerAgent-1.8-${APPD_AGENT_VERSION}.zip -d /tmp \
    && cp NAV-SDK-KTOR-${KTOR_VERSION}.jar /tmp/ver${APPD_AGENT_VERSION}/sdk-plugins/

FROM openjdk:11-jdk-slim

COPY --from=builder /tmp /opt/appdynamics