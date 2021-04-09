#!/bin/bash

brew install qemu gcc libvirt virt-viewer
echo 'security_driver = "none"' >> /usr/local/etc/libvirt/qemu.conf
echo "dynamic_ownership = 0" >> /usr/local/etc/libvirt/qemu.conf
echo "remember_owner = 0" >> /usr/local/etc/libvirt/qemu.conf
brew services start libvirt