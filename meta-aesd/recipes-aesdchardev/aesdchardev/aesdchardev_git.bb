# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8ed1a118f474eea5e159b560c339329b \
                    file://assignment-autotest/LICENSE;md5=cde0fddafb4332f35095da3d4fa989dd \
                    file://assignment-autotest/Unity/LICENSE.txt;md5=b7dd0dffc9dda6a87fa96e6ba7f9ce6c"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-diogomatos3;protocol=ssh;branch=main \
           file://aesdcharinit.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "fe7bc8e73a0b1a021d6259fdc105260730d6218d"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

FILES:${PN} += "${base_bindir}/aesdchar_load"
FILES:${PN} += "${base_bindir}/aesdchar_unload"
FILES:${PN} += "${sysconfdir}/init.d/aesdcharinit.sh"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdcharinit.sh"

do_install() {
    module_do_install
        install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/aesdcharinit.sh ${D}${sysconfdir}/init.d    
    install -d ${D}${base_bindir}
        install -m 0755 ${S}/aesd-char-driver/aesdchar_load ${D}${base_bindir}
        install -m 0755 ${S}/aesd-char-driver/aesdchar_unload ${D}${base_bindir}
}