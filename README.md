# Virsh-Gui
A gui for Virsh so you can start, stop and connect to vm's.

# Features:
Show resources assigned to a machine:
 - CPU's
 - Amount of RAM
 - Disks:
    - Type of disk
    - Location
    - Type of storage
 - Forwarded Ports

## Depends on:
- libvirt
- QEMU
- virt-viewer

## How to install dependencies on MacOS:
- First, install homebrew, which is a package manager for macOS.
- Run `brew install qemu gcc libvirt virt-viewer`.

Since macOS doesn't support QEMU security features, we need to disable them:
- `echo 'security_driver = "none"' >> /usr/local/etc/libvirt/qemu.conf`
- `echo "dynamic_ownership = 0" >> /usr/local/etc/libvirt/qemu.conf`
- `echo "remember_owner = 0" >> /usr/local/etc/libvirt/qemu.conf`
  
Finally start the libvirt service, with `brew services start libvirt`. It will start after boot as well.

### How to add a machine:
Follow this awsome guide: https://www.naut.ca/blog/2020/08/26/ubuntu-vm-on-macos-with-libvirt-qemu/

# Planned features:
- Edit VM configuration:
  - Edit Vm resources
  - Add/remove disks
- Darkmode

## Distant future features:
- Create / destroy machines:
  - Configurator 
