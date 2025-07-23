# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Specifies the source code location (a GitHub repo) and includes a local script
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-diogomatos3;protocol=ssh;branch=main"

# Set the version and specific git commit to use.
PV = "1.0+git${SRCPV}"
SRCREV = "4881d14ea5e11d19bcd72041efb035a69f250a4c"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
S = "${WORKDIR}/git/aesd-char-driver"

# Startup
inherit update-rc.d
inherit module

# Lists files to be included in the package (kernel module loader/unloader scripts and the init script).
FILES:${PN} += "${base_bindir}/aesdchar_load"
FILES:${PN} += "${base_bindir}/aesdchar_unload"
FILES:${PN} += "${sysconfdir}/init.d/aesdchar"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar"

# Passes extra variables to make for building the kernel module.
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
    install -d ${D}${base_bindir}
    install -d ${D}${sysconfdir}/init.d

    install -m 0755 ${S}/aesdchar_load ${D}${base_bindir}/
    install -m 0755 ${S}/aesdchar_unload ${D}${base_bindir}/
    
    install -m 0755 ${S}/aesdchar-start-stop ${D}${sysconfdir}/init.d/aesdchar

}