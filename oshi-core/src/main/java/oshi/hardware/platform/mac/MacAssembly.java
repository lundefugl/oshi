package oshi.hardware.platform.mac;

import oshi.hardware.common.AbstractAssembly;
import oshi.util.ExecutingCommand;

/**
 * Created by IntelliJ IDEA.
 * User: SchiTho1
 * Date: 14.10.2016
 * Time: 07:58
 * <p>
 * Copyright 2008-2013 - Securiton AG all rights reserved
 */
final class MacAssembly extends AbstractAssembly {

    private static final String CMD_SYSTEM_PROFILER_SPHARDWARE_DATA_TYPE = "system_profiler SPHardwareDataType";

    MacAssembly() {

        init();
    }

    private void init() {

//        $ system_profiler SPHardwareDataType
//        Hardware:
//
//            Hardware Overview:
//
//              Model Name: MacBook Pro
//              Model Identifier: MacBookPro8,2
//              Processor Name: Intel Core i7
//              Processor Speed: 2.3 GHz
//              Number of Processors: 1
//              Total Number of Cores: 4
//              L2 Cache (per Core): 256 KB
//              L3 Cache: 8 MB
//              Memory: 16 GB
//              Boot ROM Version: MBP81.0047.B2C
//              SMC Version (system): 1.69f4
//              Serial Number (system): C02FH4XYCB71
//              Hardware UUID: D92CE829-65AD-58FA-9C32-88968791B7BD
//              Sudden Motion Sensor:
//                  State: Enabled


        setManufacturer("Apple Inc.");

        final String modelName = parseCommandOutput(CMD_SYSTEM_PROFILER_SPHARDWARE_DATA_TYPE, "Model Name:");
        final String modelIdentifier = parseCommandOutput(CMD_SYSTEM_PROFILER_SPHARDWARE_DATA_TYPE, "Model Identifier:");
        if (modelName != null && !modelName.isEmpty()) {

            if (modelIdentifier != null && ! modelIdentifier.isEmpty()) {
                setModel(modelName + " (" + modelIdentifier+")");
            } else {
                setModel(modelName);
            }
        }
        else {
            if (modelIdentifier != null && ! modelIdentifier.isEmpty()) {
                setModel(modelIdentifier);
            }
        }

        final String serialNumberSystem = parseCommandOutput(CMD_SYSTEM_PROFILER_SPHARDWARE_DATA_TYPE, "Serial Number (system):");
        if (serialNumberSystem != null && !serialNumberSystem.isEmpty()) {
            setSerialNumber(serialNumberSystem);
        }
    }

    private String parseCommandOutput(final String nativeCall, final String marker) {
        for (final String checkLine : ExecutingCommand.runNative(nativeCall)) {
            if (checkLine.contains(marker)) {
                return checkLine.split(marker)[1].trim();
            }
        }

        return null;
    }
}
