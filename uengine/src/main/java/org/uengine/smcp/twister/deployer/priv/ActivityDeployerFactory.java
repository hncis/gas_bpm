package org.uengine.smcp.twister.deployer.priv;

import org.smartcomps.twister.common.util.StringUtil;
import org.smartcomps.twister.common.util.logger.Logger;
import org.smartcomps.twister.deployer.exception.DeploymentException;
import org.smartcomps.twister.common.util.StringUtil;
import org.smartcomps.twister.common.util.logger.Logger;

public class ActivityDeployerFactory {

    private static Logger log = Logger.getLogger(ActivityDeployerFactory.class);

    public static ActivityDeployer getActivityDeployer(String activityName) throws DeploymentException {
        Object activityDeployer = null;
        try {
            String strActivityName = StringUtil.capitalizeFirstCharacter(activityName);
            log.debug("Instantiate : " + "org.uengine.smcp.twister.deployer.priv." + strActivityName + "Deployer");
            activityDeployer = Class.forName("org.uengine.smcp.twister.deployer.priv." + strActivityName + "Deployer").newInstance();
        } catch (Exception e) {
            throw new DeploymentException("Unable to instanciate the activity deployer due to " + e.getMessage(), e);
        }
        return (ActivityDeployer) activityDeployer;
    }

}
