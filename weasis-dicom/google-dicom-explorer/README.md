### About Weasis Google DICOM Plugin
Plugin enables Weasis Viewer users access to [Google Cloud Healthcare API](https://cloud.google.com/healthcare) DICOM data.  
It uses [DICOMweb REST API](https://cloud.google.com/healthcare/docs/how-tos/dicomweb) to interact with Google Cloud services.

#### Setting up access to Google Healthcare data:
* open [Google Cloud Console](https://console.cloud.google.com/apis/credentials) 
* create new OAuth Client ID (Application Type: Other). 
* Download **_client_secrets.json_** and place it in Weasis root folder (next to viewer-win32.exe and viewer-linux.sh)
* Launch Weasis Viewer
* Switch to **_Google Dicom Explorer_** tab and login using your Google Account
* Explore your DICOM data

Refer to [access-control](https://cloud.google.com/healthcare/docs/concepts/access-control) section of 
Cloud Healthcare API Documentation for additional information.