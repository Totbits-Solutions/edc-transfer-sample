/*
 *  Copyright (c) 2021 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - Initial implementation
 *
 */

package org.eclipse.edc.sample.extension.listener;

import org.eclipse.edc.connector.controlplane.transfer.spi.observe.TransferProcessListener;
import org.eclipse.edc.connector.controlplane.transfer.spi.observe.TransferProcessStartedData;
import org.eclipse.edc.connector.controlplane.transfer.spi.types.TransferProcess;
import org.eclipse.edc.spi.monitor.Monitor;

public class TransferProcessStartedListener implements TransferProcessListener {

    private final Monitor monitor;

    public TransferProcessStartedListener(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void initiated(TransferProcess process) {
        // consumer & provider
        monitor.info("[EVENT: INITIATED] Transfer Process initiated - ID: " + process.getId() + ", State: " + process.getState());
        monitor.info("[EVENT: INITIATED] Process details: " + process.toString());
    }

    @Override
    public void provisioningRequested(TransferProcess process) {
        monitor.info("[EVENT: PROVISIONING_REQUESTED] Transfer Process provisioning requested - ID: " + process.getId() + ", State: " + process.getState());
        monitor.info("[EVENT: PROVISIONING_REQUESTED] Process details: " + process.toString());
    }

    @Override
    public void provisioned(TransferProcess process) {
        // provider & consumer
        monitor.info("[EVENT: PROVISIONED] Transfer Process provisioned - ID: " + process.getId() + ", State: " + process.getState());
        monitor.info("[EVENT: PROVISIONED] Process details: " + process.toString());
    }

    @Override
    public void requested(TransferProcess process) {
        // consumer
        monitor.info("[EVENT: REQUESTED] Transfer Process requested - ID: " + process.getId() + ", State: " + process.getState());
        monitor.info("[EVENT: REQUESTED] Process details: " + process.toString());
    }

    @Override
    public void started(TransferProcess process, TransferProcessStartedData additionalData) {
        //consumer & provider
        monitor.info("[EVENT: STARTED] Transfer Process started - ID: " + process.getId() + ", State: " + process.getState());
        monitor.info("[EVENT: STARTED] Additional data: " + additionalData);
        monitor.info("[EVENT: STARTED] Process details: " + process.toString());
    }

    @Override
    public void completed(TransferProcess process) {
        // consumer & provider
        monitor.info("[EVENT: COMPLETED] Transfer Process completed - ID: " + process.getId() + ", State: " + process.getState());
        monitor.info("[EVENT: COMPLETED] Process details: " + process.toString());
    }

    @Override
    public void terminated(TransferProcess process) {
        monitor.info("[EVENT: TERMINATED] Transfer Process terminated - ID: " + process.getId() + ", State: " + process.getState());
        monitor.info("[EVENT: TERMINATED] Process details: " + process.toString());
    }

    @Override
    public void suspended(TransferProcess process) {
        monitor.info("[EVENT: SUSPENDED] Transfer Process suspended - ID: " + process.getId() + ", State: " + process.getState());
        monitor.info("[EVENT: SUSPENDED] Process details: " + process.toString());
    }

    @Override
    public void deprovisioningRequested(TransferProcess process) {
        monitor.info("[EVENT: DEPROVISIONING_REQUESTED] Transfer Process deprovisioning requested - ID: " + process.getId() + ", State: " + process.getState());
        monitor.info("[EVENT: DEPROVISIONING_REQUESTED] Process details: " + process.toString());
    }

    @Override
    public void deprovisioned(TransferProcess process) {
        //provider
        monitor.info("[EVENT: DEPROVISIONED] Transfer Process deprovisioned - ID: " + process.getId() + ", State: " + process.getState());
        monitor.info("[EVENT: DEPROVISIONED] Process details: " + process.toString());
    }
}
