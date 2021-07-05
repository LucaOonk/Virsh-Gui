# Virt Commander
A gui for QEMU/Libvirt made in Java so you can start, stop, add, remove to virsh and connect to them.
I made this because i had troubles getting the virt-manager to work on MacOS, so i made my own.

This is far from production ready so beware.

![alt text](https://github.com/LucaOonk/LucaOonk.github.io/blob/master/depictions/Virsh-GUI/Interface.png)

# Features:
Show resources assigned to a machine:
 - CPU's
 - Amount of RAM
 - Disks:
    - Type of disk
    - Location
    - Type of storage
 - Forwarded Ports
 - Create / destroy machines:
 - Configurator:
  - Add new diskfiles or select exsisting ones. 
 - Supports remote management with Virt Server https://github.com/LucaOonk/Virt-Server

## Depends on:
- libvirt https://www.libvirt.org
- QEMU https://www.qemu.org
- virt-viewer

## Compatibility
Works on MacOS 11.
MacOS 12 is not supported for now as the dependencies are not ready yet.

## How to install dependencies on MacOS:
- First, install homebrew, which is a package manager for macOS.
- Run `brew install qemu gcc libvirt virt-viewer`.

Since macOS doesn't support QEMU security features, we need to disable them:
- `echo 'security_driver = "none"' >> /usr/local/etc/libvirt/qemu.conf`
- `echo "dynamic_ownership = 0" >> /usr/local/etc/libvirt/qemu.conf`
- `echo "remember_owner = 0" >> /usr/local/etc/libvirt/qemu.conf`
  
Finally start the libvirt service, with `brew services start libvirt`. It will start after boot as well.

# Download:
Releases can be found here: https://github.com/LucaOonk/Virt-Commander/releases

# Planned features:
- Show disk size
- Edit VM configuration:
  - Edit Vm resources
  - Add/remove disks
- Darkmode

## Distant future features:
- VM-usage graphs

# Known Issues:
- Drives not being added to a VM when using the configurator
