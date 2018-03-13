package dk.grp1.tanks.core.internal;

import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.services.IGamePluginService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danie on 12-03-2018.
 */
public class ServiceLoader  {
    private BundleContext bundleContext;

    public ServiceLoader(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public List<IEntityProcessingService> getEntityProcessingServices(){
        ArrayList<IEntityProcessingService> processingServices = new ArrayList<>();
        ServiceReference[] serviceReferences = new ServiceReference[1];
        try {
            serviceReferences = bundleContext.getAllServiceReferences(IEntityProcessingService.class.getName(),null);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
        if(serviceReferences != null) {
            for (ServiceReference s : serviceReferences) {
                 processingServices.add((IEntityProcessingService) bundleContext.getService(s));
            }
        }
        return processingServices;
    }

    public List<IGamePluginService> getGamePluginServices(){
        ArrayList<IGamePluginService> pluginServices = new ArrayList<>();
        ServiceReference[] serviceReferences = new ServiceReference[1];
        try {
            serviceReferences = bundleContext.getAllServiceReferences(IGamePluginService.class.getName(),null);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
        if(serviceReferences != null) {
            for (ServiceReference s : serviceReferences) {
                pluginServices.add((IGamePluginService) bundleContext.getService(s));
            }
        }
        return pluginServices;
    }

}
