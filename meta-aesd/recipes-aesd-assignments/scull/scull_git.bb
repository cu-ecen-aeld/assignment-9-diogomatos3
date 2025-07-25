# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Specifies the source code location (a GitHub repo) and includes a local script
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-diogomatos3;protocol=ssh;branch=master"

# Set the version and specific git commit to use.
PV = "1.0+git${SRCPV}"
SRCREV = "d4bd861de324d0a097c1eda729b5876e49550c6e"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
S = "${WORKDIR}/git/scull"

RPROVIDES:${PN} += "kernel-module-scull"

# Startup
inherit module
inherit update-rc.d

# Lists files to be included in the package (kernel module loader/unloader scripts and the init script).
FILES:${PN} += "${bindir}/scull_load"
FILES:${PN} += "${bindir}/scull_unload"
FILES:${PN} += "${sysconfdir}/init.d/scull"

INITSCRIPT_NAME = "scull"
INITSCRIPT_PARAMS = "defaults"

# Passes extra variables to make for building the kernel module.
EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_DIR} -C ${STAGING_KERNEL_DIR} M=${S}"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install:append () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/scull_load ${D}${bindir}/
    install -m 0755 ${S}/scull_unload ${D}${bindir}/

    install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/scull-start-stop ${D}${sysconfdir}/init.d/scull
}