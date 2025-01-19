import os
import re
import shutil
import subprocess

instance_dir = './buildOut/instances'

def nextInstanceNum():
    all_items = os.listdir(instance_dir)
    folders = [item for item in all_items if os.path.isdir(os.path.join(instance_dir, item))]

    if not folders:
        return 1
    
    pattern = re.compile(rf'^{re.escape('Test')}\s*(\d+)$')
        
    numbers = []
    for folder in folders:
        match = pattern.match(folder)
        if match:
            numbers.append(int(match.group(1)))

    return max(numbers) + 1

def createConfiguration(name: str):
    cfg = f'''
        InstanceType=OneSix
        JavaPath=Replace this with your java path
        LogPrePostOutput=true
        ManagedPack=false
        ManagedPackID=
        ManagedPackName=
        ManagedPackType=
        ManagedPackVersionID=
        ManagedPackVersionName=
        iconKey=default
        lastLaunchTime=0
        lastTimePlayed=0
        name={name}
        notes=
        totalTimePlayed=0
    '''
    with open(f'{instance_dir}/{name}/instance.cfg', 'w', encoding='utf-8') as file:
        file.write(cfg)

def createMMCPackJson(name: str):
    shutil.copy(
        f'./build/instance/mmc-pack.json',
        f'{instance_dir}/{name}/mmc-pack.json'
    )


def newInstance(number: int):
    name = f'Test {number}'
    os.makedirs(f'{instance_dir}/{name}')

    # Configurations
    createConfiguration(name)
    createMMCPackJson(name)

    minecraft = f'{instance_dir}/{name}/minecraft'
    os.makedirs(minecraft)

    os.chdir('build/server')
    subprocess.run(['java', '-jar', 'packwiz-installer-bootstrap.jar', '../../pack.toml'], check=True)
    os.chdir('../..')

    for folder in ['config', 'groovy', 'journeymap', 'mods', 'resourcepacks', 'resources', 'structures']:
            shutil.copytree(f'./build/server/{folder}', f'{instance_dir}/{name}/minecraft/{folder}')