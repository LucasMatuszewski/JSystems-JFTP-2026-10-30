/*
 * Copyright 2012 jMethods, Inc. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.myjavaworld.jftp;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/** Describes the fixed appearance of the internal desktop application. */
public class UIPrefsPanel extends JPanel {
    public UIPrefsPanel() {
        super(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(new JLabel("JSystems Light"), BorderLayout.NORTH);
        JLabel description = new JLabel("The JSystems appearance applies to all sessions.");
        description.setVerticalAlignment(SwingConstants.TOP);
        add(description, BorderLayout.CENTER);
    }

    public boolean validateFields() { return true; }

    public void populateScreen() { }

    public void populateScreen(JFTPPreferences prefs) { }

    public void saveChanges() {
        // Preserve serialized legacy appearance settings when saving other preferences.
    }
}
