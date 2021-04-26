package org.bsuir.view;

import org.apache.log4j.Logger;
import org.bsuir.controller.ServerController;
import org.bsuir.util.TextAreaLogAppender;
import org.bsuir.server.Server;
import org.bsuir.storage.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainWindow {
    private final JFrame mainWindow;
    private final TextAreaLogAppender logAppender;

    private Server server;

    public MainWindow() {
        this.mainWindow = new JFrame("Custom server");
        this.mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        mainWindow.setContentPane(contentPane);
        contentPane.setLayout(new FlowLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton onButton = new JButton("Turn on");
        JButton offButton = new JButton("Turn off");

        onButton.setForeground(Color.BLACK);
        onButton.setBackground(Color.WHITE);
        onButton.setBorder(new RoundedBorder(5));

        offButton.setForeground(Color.BLACK);
        offButton.setBackground(Color.WHITE);
        offButton.setBorder(new RoundedBorder(5));

        JTextArea logArea = new JTextArea("", 40, 100);
        logArea.setLineWrap(true);

        Logger logger = Logger.getRootLogger();
        this.logAppender = (TextAreaLogAppender) logger.getAppender("myAppender");
        this.logAppender.setAreaToLogTo(logArea);

        onButton.addActionListener(this.getOnAction());
        offButton.addActionListener(this.getOffAction());

        JScrollPane scrollPane = new JScrollPane(logArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(scrollPane)
                .addGroup(layout.createParallelGroup()
                        .addComponent(onButton)
                        .addComponent(offButton)
                )
        );

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(scrollPane)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(onButton)
                        .addGap(30)
                        .addComponent(offButton)
                )
        );


        mainWindow.setSize(new Dimension(1200, 600));
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }

    private ActionListener getOffAction() {
        return e -> this.server.turnOff();
    }

    private ActionListener getOnAction() {
        return e -> {
            this.server = new Server(new ServerController(new Repository()));
            server.turnOn();
            server.runServer();
        };
    }

    public JFrame getMainWindow() {
        return mainWindow;
    }

    public Server getServer() {
        return server;
    }

    public TextAreaLogAppender getLogAppender() {
        return logAppender;
    }
}
