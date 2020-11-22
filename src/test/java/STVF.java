import com.boydti.fawe.Fawe;
import com.boydti.fawe.config.Settings;
import com.boydti.fawe.object.FaweLocation;
import com.boydti.fawe.object.RegionWrapper;
import com.boydti.fawe.object.changeset.DiskStorageHistory;
import com.boydti.fawe.util.MainUtil;
import com.sk89q.worldedit.world.World;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class STVF {
    public static List<DiskStorageHistory> getBDFiles(FaweLocation origin, UUID user, int radius, long timediff, boolean shallow) {
        File history = MainUtil.getFile(Fawe.imp().getDirectory(), Settings.IMP.PATHS.HISTORY + File.separator + origin.world);
        if (!history.exists()) {
            return new ArrayList<>();
        } else {
            long now = System.currentTimeMillis();
            ArrayList<File> rawHistoryFiles = new ArrayList<>();

            for (File userFile : history.listFiles()) {
                if (userFile.isDirectory()) {
                    UUID userUUID;
                    try {
                        userUUID = UUID.fromString(userFile.getName());
                    } catch (IllegalArgumentException var25) {
                        continue;
                    }

                    if (user == null || userUUID.equals(user)) {
                        new ArrayList<>();

                        for (File file : userFile.listFiles()) {
                            if (file.getName().endsWith(".bd") && (timediff >= 2147483647L || now - file.lastModified() <= timediff)) {
                                rawHistoryFiles.add(file);
                                if (rawHistoryFiles.size() > 2048) {
                                    return null;
                                }
                            }
                        }
                    }
                }
            }

            World world = origin.getWorld();
            rawHistoryFiles.sort((a, b) -> {
                String aName = a.getName();
                String bName = b.getName();
                int aI = Integer.parseInt(aName.substring(0, aName.length() - 3));
                int bI = Integer.parseInt(bName.substring(0, bName.length() - 3));
                long value = aI - bI;
                return Long.compare(value, 0L);
            });
            RegionWrapper bounds = new RegionWrapper(origin.x - radius, origin.x + radius, origin.z - radius, origin.z + radius);
            RegionWrapper boundsPlus = new RegionWrapper(bounds.minX - 64, bounds.maxX + 512, bounds.minZ - 64, bounds.maxZ + 512);
            HashSet<RegionWrapper> regionSet = new HashSet<>(Collections.singletonList(bounds));
            ArrayList<DiskStorageHistory> dshList = new ArrayList<>();

            for (File rawHistory : rawHistoryFiles) {
                UUID uuid = UUID.fromString(rawHistory.getParentFile().getName());
                final int index = Integer.parseInt(rawHistory.getName().split("\\.")[0]);
                DiskStorageHistory storageHist = new DiskStorageHistory(world, uuid, index);
                DiskStorageHistory.DiskStorageSummary summary = storageHist.summarize(boundsPlus, shallow);
                RegionWrapper region = new RegionWrapper(summary.minX, summary.maxX, summary.minZ, summary.maxZ);
                boolean encompassed = false;
                boolean isIn = false;

                for (RegionWrapper allowed : regionSet) {
                    isIn = isIn || allowed.intersects(region);
                    if (encompassed = allowed.isIn(region.minX, region.maxX) && allowed.isIn(region.minZ, region.maxZ)) {
                        break;
                    }
                }

                if (isIn) {
                    dshList.add(0, storageHist);
                    if (!encompassed) {
                        regionSet.add(region);
                    }

                    if (shallow && dshList.size() > 64) {
                        return dshList;
                    }
                }
            }

            return dshList;
        }
    }
}
