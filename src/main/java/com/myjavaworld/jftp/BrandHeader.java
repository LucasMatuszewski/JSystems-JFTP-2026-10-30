package com.myjavaworld.jftp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;

/** Persistent application identity above the session actions. */
public final class BrandHeader extends JPanel {
    public BrandHeader() {
        super(new BorderLayout(24, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 1, 0, new Color(0x800000)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        getAccessibleContext().setAccessibleName("JSystems FTP workspace header");

        JPanel identity = new JPanel(new GridLayout(2, 1, 0, 2));
        identity.setOpaque(false);
        JLabel title = new JLabel("JSystems FTP");
        title.setForeground(new Color(0x800000));
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        identity.add(title);
        JLabel subtitle = new JLabel("Local and remote files");
        subtitle.setForeground(new Color(0x656263));
        identity.add(subtitle);
        add(identity, BorderLayout.WEST);

        FlatSVGIcon icon = new FlatSVGIcon("com/myjavaworld/jftp/branding/logo-desktop.svg", 184, 58);
        icon.setDescription("JSystems logo");
        JLabel logo = new JLabel(icon);
        logo.getAccessibleContext().setAccessibleName("JSystems logo");
        add(logo, BorderLayout.EAST);
    }
}
