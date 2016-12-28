
 Sanur v1.0.3.1 for Windows 2000/XP/2003
 Copyright (C) 2001-2003 Ritchie Lawrence
 http://www.commandline.co.uk

 Description
 -----------

 Sanur 'pipes' a password into the Windows 2000/XP/2003 Runas utility,
 thereby making Runas scriptable.

 Usage
 -----

 RUNAS <options> | SANUR password
 RUNAS <options> | SANUR /i [drive:][path]filename

 /i    Pipes the password from the specified file into RUNAS.

 Example usage:-

   runas /u:domain\username program.exe | sanur pa55w0rd
   runas /u:domain\username program.exe | sanur /i password.txt
   runas /u:domain\username "program.exe /arg1 /arg2" | sanur /i password.txt
   runas /u:domain\username "program.exe /arg1 /arg2 >log.txt" | sanur /i password.txt

 Note: As Runas uses stdout to show errors, I recommend stdout is redirected
 to stderr using >&2. This does not affect the program started by Runas. Eg:-

   runas /u:domainusername program.exe >&2 | sanur pa55w0rd

 Terms of Use
 ------------

 This software is provided "as is", without any guarantee made
 as to its suitability or fitness for any particular use. It 
 may contain bugs and use of this product is at your own risk. I 
 take no responsilbity for any damage that may be caused through
 its use. 

 This product is freeware. There is no fee for personal or
 corporate use. It may be freely distributed only in its original
 form and provided this text file is attached.


 Problems
 --------

 As of 2005-01-11, Sanur is no longer supported. If you're unable to get Sanur
 to function as expected I suggest that you seek an alternative solution. Please
 don't send me any emails relating to Sanur.

 -- Ritchie
