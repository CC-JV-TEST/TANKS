package dk.grp1.tanks.core.internal;

import dk.grp1.tanks.common.services.*;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import java.util.ArrayList;
import java.util.List;

public class ServiceLoader  {
    private BundleContext bundleContext;
    private List<IPostEntityProcessingService> postEntityProcessingServices;

    public ServiceLoader(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public List<IEntityProcessingService> getEntityProcessingServices(){
        ArrayList<IEntityProcessingService> processingServices = new ArrayList<>();
        ServiceReference[] serviceReferences = new ServiceReference[5];
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

    public List<IPostEntityProcessingService> getPostEntityProcessingServices() {
        ArrayList<IPostEntityProcessingService> processingServices = new ArrayList<>();
        ServiceReference[] serviceReferences = new ServiceReference[5];
        try {
            serviceReferences = bundleContext.getAllServiceReferences(IPostEntityProcessingService.class.getName(),null);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
        if(serviceReferences != null) {
            for (ServiceReference s : serviceReferences) {
                processingServices.add((IPostEntityProcessingService) bundleContext.getService(s));
            }
        }
        return processingServices;
    }

    public List<IWeapon> getIWeaponServices() {
        ArrayList<IWeapon> weapons = new ArrayList<>();
        ServiceReference[] serviceReferences = new ServiceReference[5];
        try {
            serviceReferences = bundleContext.getAllServiceReferences(IWeapon.class.getName(), null);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }

        if(serviceReferences != null) {
            for (ServiceReference s : serviceReferences) {
                weapons.add((IWeapon) bundleContext.getService(s));
            }
        }
        return weapons;
    }

    public List<INonEntityProcessingService> getNonEntityProcessingServices() {
        ArrayList<INonEntityProcessingService> processingServices = new ArrayList<>();
        ServiceReference[] serviceReferences = new ServiceReference[5];
        try {
            serviceReferences = bundleContext.getAllServiceReferences(INonEntityProcessingService.class.getName(),null);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
        if(serviceReferences != null) {
            for (ServiceReference s : serviceReferences) {
                processingServices.add((INonEntityProcessingService) bundleContext.getService(s));
            }
        }
        return processingServices;
    }

    public List<IRoundService> getRoundEndServices() {
        ArrayList<IRoundService> processingServices = new ArrayList<>();
        ServiceReference[] serviceReferences = new ServiceReference[5];
        try {
            serviceReferences = bundleContext.getAllServiceReferences(IRoundService.class.getName(),null);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
        if(serviceReferences != null) {
            for (ServiceReference s : serviceReferences) {
                processingServices.add((IRoundService) bundleContext.getService(s));
            }
        }
        return processingServices;
    }
}
