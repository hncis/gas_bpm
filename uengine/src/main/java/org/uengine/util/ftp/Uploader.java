package org.uengine.util.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpDirEntry;
import sun.net.ftp.FtpDirParser;
import sun.net.ftp.FtpProtocolException;
import sun.net.ftp.FtpReplyCode;

/**
 * @author Jinyoung Jang
 */

@SuppressWarnings("restriction")
public class Uploader extends FtpClient {
    public Uploader() {
        super();
    }

    public void connect(String server, String user, String pass) throws Exception {
        // openServer(server, 21);
        // login(user, pass);
    }

    public void uploadFile(String fileName, String directory) throws Exception {
        uploadFile(new File(fileName), directory);
    }

    public void uploadFile(File file, String directory) throws Exception {
        uploadFile(new FileInputStream(file), directory);
    }

    public void uploadFile(InputStream is, String directory) throws Exception {
        is.close();
    }

    @Override
    public FtpClient abort() throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient allocate(long arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient appendFile(String arg0, InputStream arg1) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient changeDirectory(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient changeToParentDirectory() throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public FtpClient completePending() throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient connect(SocketAddress arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient connect(SocketAddress arg0, int arg1) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient deleteFile(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient enablePassiveMode(boolean arg0) {
        return null;
    }

    @Override
    public FtpClient endSecureSession() throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public int getConnectTimeout() {
        return 0;
    }

    @Override
    public List<String> getFeatures() throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient getFile(String arg0, OutputStream arg1) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public InputStream getFileStream(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public String getHelp(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public String getLastFileName() {
        return null;
    }

    @Override
    public Date getLastModified(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpReplyCode getLastReplyCode() {
        return null;
    }

    @Override
    public String getLastResponseString() {
        return null;
    }

    @Override
    public long getLastTransferSize() {
        return 0;
    }

    @Override
    public Proxy getProxy() {
        return null;
    }

    @Override
    public int getReadTimeout() {
        return 0;
    }

    @Override
    public SocketAddress getServerAddress() {
        return null;
    }

    @Override
    public long getSize(String arg0) throws FtpProtocolException, IOException {
        return 0;
    }

    @Override
    public String getStatus(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public String getSystem() throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public String getWelcomeMsg() {
        return null;
    }

    @Override
    public String getWorkingDirectory() throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public boolean isPassiveModeEnabled() {
        return false;
    }

    @Override
    public InputStream list(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public Iterator<FtpDirEntry> listFiles(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient login(String arg0, char[] arg1) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient login(String arg0, char[] arg1, String arg2) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient makeDirectory(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public InputStream nameList(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient noop() throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient putFile(String arg0, InputStream arg1, boolean arg2) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public OutputStream putFileStream(String arg0, boolean arg1) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient reInit() throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient removeDirectory(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient rename(String arg0, String arg1) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient setConnectTimeout(int arg0) {
        return null;
    }

    @Override
    public FtpClient setDirParser(FtpDirParser arg0) {
        return null;
    }

    @Override
    public FtpClient setProxy(Proxy arg0) {
        return null;
    }

    @Override
    public FtpClient setReadTimeout(int arg0) {
        return null;
    }

    @Override
    public FtpClient setRestartOffset(long arg0) {
        return null;
    }

    @Override
    public FtpClient setType(TransferType arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient siteCmd(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient startSecureSession() throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient structureMount(String arg0) throws FtpProtocolException, IOException {
        return null;
    }

    @Override
    public FtpClient useKerberos() throws FtpProtocolException, IOException {
        return null;
    }
}
