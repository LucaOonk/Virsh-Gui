# Virsh-Gui
A gui for Virsh made in Java so you can start, stop, add, remove to virsh and connect to them.
I made this because i had troubles getting the virt-manager to work on MacOS, so i made my own.

![alt text](https://github.com/LucaOonk/LucaOonk.github.io/blob/master/depictions/Virsh-GUI/GUI.png)

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
  - Configurator 

## Depends on:
- libvirt https://www.libvirt.org
- QEMU https://www.qemu.org
- virt-viewer

## How to install dependencies on MacOS:
- First, install homebrew, which is a package manager for macOS.
- Run `brew install qemu gcc libvirt virt-viewer`.

Since macOS doesn't support QEMU security features, we need to disable them:
- `echo 'security_driver = "none"' >> /usr/local/etc/libvirt/qemu.conf`
- `echo "dynamic_ownership = 0" >> /usr/local/etc/libvirt/qemu.conf`
- `echo "remember_owner = 0" >> /usr/local/etc/libvirt/qemu.conf`
  
Finally start the libvirt service, with `brew services start libvirt`. It will start after boot as well.

# Download:
Releases can be found here: https://github.com/LucaOonk/Virsh-Gui/releases

### How to add a machine:
Follow this awsome guide: https://www.naut.ca/blog/2020/08/26/ubuntu-vm-on-macos-with-libvirt-qemu/

# Planned features:
- Reworked VM-Configurator
- Show disk size
- Edit VM configuration:
  - Edit Vm resources
  - Add/remove disks
- Darkmode
- Working default save location for the xml files

## Distant future features:
- VM-usage graphs

# Kown Issues:
- Default vm location not working properly
