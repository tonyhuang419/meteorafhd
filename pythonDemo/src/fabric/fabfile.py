from fabric.api import run

def dir():
    try:
        cmd_output = run("dir")
        print cmd_output
    except Exception as ex: 
        print ( ex )
        
dir();