from socket import *
import thread

#import pri
import string,cgi,time,urlparse, mimetypes, socket, sys, urllib, urllib2, commands, thread 
from os import curdir, sep
from BaseHTTPServer import HTTPServer, BaseHTTPRequestHandler
from SocketServer import ThreadingMixIn
import threading

port = 9020

class MyHandler(BaseHTTPRequestHandler):

    def do_GET(self):
        
         #socket connection set up 
        m = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        m.connect(('google.com', 0))
        host = m.getsockname()[0]
        port = m.getsockname()[1]
        s.bind((host,port))
        #s.bind(('',50000)) if no internet use this line and comment line 61 through 63
        s.listen(20) 
        print "New connection from host: " + str(host) + " port: " + str(port)
        
        #appending all threads for safety execution    
        print threading.currentThread().getName()
        #threadserver.append(threading.currentThread())
        
        
        
        
        try:
            if self.path == "/": 
                f = open("index.html")
                
                self.send_response(200)
                self.send_header('Content-type', 'text/html')
                self.end_headers()
                
                self.wfile.write(f.read())
                f.close()
                s.close()
                return
            
            

            if self.path[:5] == "/CHAT":
                
                
                parsed_path = urlparse.urlparse(self.path)
                
                """ Will Enter here only if CHAT? query is set"""
                if parsed_path.query:
                             
                    parsed =  parsed_path.query
                    parsedSplit = parsed.split(';')
                    
                    name = parsedSplit[0].replace('=',' ')
                    name = name.split(' ')
                    finalName = name[1]
                    finalName = finalName.replace('+',' ')
                    

                    
                
                    line = parsedSplit[1].replace('=',' ')
                    line = line.split(' ')
                    finalLine = line[1]
                    finalLine = finalLine.replace('+',' ')
                    finalLine = ("<p>" + finalName + ": " +  getEmot(finalLine) + "</p>")
                    
                    chatBuffer.append(finalLine)
                    
                
                    #opens file and shoves the chat buffer in
                    f = list()
                    f = open("index.html").readlines()
                    
                    number = f.index("\t\tName: <input type='text' id='name' name='name' value='\n" ,)
                    f[number+1]=finalName
                    
                    
                    number = f.index("<div class='chatarea' id='chatarea' >\n", )
                    
                    f[number+1]="".join(chatBuffer)
                    result = '\n'.join(f)
                    self.send_response(200)
                    self.send_header('Content-type', 'text/html')
                    self.end_headers()
                    
                    self.wfile.write(result)
                    
                    s.close()
                    return
            #AJAX call
            if self.path[:8] == "/REFRESH":  
                
                self.wfile.write("".join(chatBuffer))
                s.close()
                return
                
                
                      
            else:
                parsed_path = urlparse.urlparse(self.path)    
                thePath = parsed_path.path[1:]
                
                f = open( thePath)
                
                import mimetypes
                mime = mimetypes.guess_type(thePath)
                
                self.send_response(200)
                self.send_header('Content-type', mime)
                self.end_headers()
                
                self.wfile.write(f.read())
                f.close()
               
                
                s.close()
                return   
            return
                
        except IOError:
            self.send_error(404,'File Not Found: %s' % self.path)
     
def getEmot(String):
    String = String.replace(":)","<img src='emoticons/smile.jpg' alt='' />")
    String = String.replace(":(","<img src='emoticons/frown.jpg' alt='' />")
    String = String.replace("^_^","<img src='emoticons/glee.jpg' alt='' />")
    String = String.replace(":D","<img src='emoticons/bigsmile.jpg' alt='' />")
    String = String.replace(":p","<img src='emoticons/tongue.jpg' alt='' />")
    return String




global ipList, joinedList, chatLine,chatBuffer, runOnce 

runOnce =0
joinedList = list()

chatBuffer = list() 
print "Welcome: Listening on TCP port " + str(port) + "..."


class ThreadedHTTPServer(ThreadingMixIn, HTTPServer):
    """Handle requests in a separate thread."""
    
#Main()    
server = ThreadedHTTPServer(('', 9020), MyHandler)
server.serve_forever()
