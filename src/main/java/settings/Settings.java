/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

/**
 *
 * @author Cedric Jansen
 */
public class Settings implements java.io.Serializable {
    
    private String applicationTitle;
    private String applicationVersion;
    private String applicationLicensee;

    public String getApplicationTitle() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getApplicationLicensee() {
        return applicationLicensee;
    }

    public void setApplicationLicensee(String applicationLicensee) {
        this.applicationLicensee = applicationLicensee;
    }
    
    
    
}
