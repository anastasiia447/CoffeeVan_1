package view;

import helpers.AppLogger;
import main.Application;
import model.Coffee;
import model.CoffeeGoodsToListView;
import model.Van;
import model.db.Repository;
import model.enumerators.AggregateState;
import view.coffee.AddCoffeeForm;
import view.coffee.ChangeCoffeeForm;
import view.coffee.ShowCoffeeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

import static helpers.Extension.parseFloat;
import static helpers.Extension.parseInt;
import static model.db.Repository.selectAllCoffeeSQL;
import static model.db.Repository.selectAllVanSQL;

public class AppGUI extends Frame {
    private final Application app;
    private int checkedCoffeeObject;
    private int checkedVanObject;
    private ArrayList<Coffee> coffees;

    public AppGUI(Application app) {
        this.app = app;
        initCoffeeList(getAllCoffees());
        initVanList();

        /* Buttons - coffee */
        addCoffeeButton.addActionListener(l -> addCoffee());
        changeCoffeeButton.addActionListener(l -> changeCoffee());
        deleteCoffeeButton.addActionListener(l -> deleteCoffee());

        /* SORT BUTTON */
        sortByQualityButton.addActionListener(l -> sortCoffeeByQuality());
        sortByCorrelationButton.addActionListener(l -> sortCoffeeByCorrelation());

        /* SEARCHERS */
        findByNameButton.addActionListener(l -> findCoffeeByName());
        findByNameAndStateButton.addActionListener(l -> findCoffeeByState());
        findByQualityMarkButton.addActionListener(l -> findCoffeeByQuality());
        findByQualityMarkRangeButton.addActionListener(l -> findCoffeeByQualityRange());
        findByCorrelationRangeButton.addActionListener(l -> findCoffeeByCorrelation());

        /* VAN */
        addVanButton.addActionListener(l -> addVan());
        changeVanButton.addActionListener(l -> changeVan());
        deleteVanButton.addActionListener(l -> deleteVan());
        loadVanButton.addActionListener(l -> loadVan());

        /* REFRESH */
        refreshButton.addActionListener(l -> onRefreshLists());

        /* SETTINGS */
        settingsButton.addActionListener(l -> openSettingFrame());
    }

    /* COFFEE METHODS */
    private void addCoffee() {
        addCoffeeFrame = new JFrame("CoffeeVan --- Add van");
        addCoffeeFrame.setContentPane(new AddCoffeeForm(app).getAddPanel());
        addCoffeeFrame.setLocationRelativeTo(null);
        addCoffeeFrame.pack();
        addCoffeeFrame.setVisible(true);
        initCoffeeList(getAllCoffees());
    }

    private void changeCoffee() {
        changeCoffeeFrame = new JFrame("CoffeeVan --- Change coffee");
        changeCoffeeFrame.setContentPane(new ChangeCoffeeForm(coffees.get(checkedCoffeeObject).getId(), app).getAddPanel());
        changeCoffeeFrame.setLocationRelativeTo(null);
        changeCoffeeFrame.pack();
        changeCoffeeFrame.setVisible(true);
        initCoffeeList(getAllCoffees());
    }

    private void deleteCoffee() {
        if (coffees != null) {
            app.getManager().deleteCoffee(coffees.get(checkedCoffeeObject).getId());
        }
    }

    private void sortCoffeeByQuality() {
        sortByQualityButton.addActionListener(l -> {
            coffees = app.getManager().sortCoffeeByQuality();
            initCoffeeList(coffees);
            coffee_list_fragment.updateUI();
        });
    }

    private void sortCoffeeByCorrelation() {
        sortByCorrelationButton.addActionListener(l -> {
            coffees = app.getManager().sortCoffeeByCorrelation();
            initCoffeeList(coffees);
            coffee_list_fragment.updateUI();
        });
    }

    private void findCoffeeByName() {
        var getNameFrame = new JFrame("CoffeeVan --- Find by name");
        String result = (String) JOptionPane.showInputDialog(
                getNameFrame,
                "Enter name:");
        if (!Objects.equals(result, "")) {
            initCoffeeList(app.getManager().findCoffee(result));
            coffee_list_fragment.updateUI();
        }
    }

    private void findCoffeeByState() {
        Object[] states = new Object[4];
        states[0] = AggregateState.cereal;
        states[1] = AggregateState.ground;
        states[2] = AggregateState.solubleInCans;
        states[3] = AggregateState.solubleInSachets;

        var getDataFrame = new JFrame("Find by name & aggregate state");
        String name = JOptionPane.showInputDialog(getDataFrame, "Enter name: ");
        int stateIndex = JOptionPane.showOptionDialog(getDataFrame, "Enter aggregate state: ",
                "CoffeeVan", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, states, null);
        if (stateIndex != -1 && !Objects.equals(name, "")) {
            String temp = states[stateIndex].toString();
            initCoffeeList(app.getManager().findCoffee(name, AggregateState.valueOf(temp)));
            coffee_list_fragment.updateUI();
        }
    }

    private void findCoffeeByQuality() {
        var getDataFrame = new JFrame();
        String result = JOptionPane.showInputDialog(getDataFrame, "Enter quality mark: \n" +
                "Min value - 0; Max value - 100");

        if (!Objects.equals(result, "")) {
            int qualityMark = parseInt(result);
            if (qualityMark >= 0 && qualityMark <= 100) {
                initCoffeeList(app.getManager().findCoffee(qualityMark));
                coffee_list_fragment.updateUI();
            }
        }
    }

    private void findCoffeeByQualityRange() {
        var getDataFrame = new JFrame();
        String minValue = JOptionPane.showInputDialog(getDataFrame, "Enter quality min value: ");
        String maxValue = JOptionPane.showInputDialog(getDataFrame, "Enter quality max value: ");
        if (!Objects.equals(minValue, "") && maxValue != "") {
            int min = parseInt(minValue);
            int max = parseInt(maxValue);
            if (min >= 0 && min < 100 && max <= 100 && max > 0) {
                initCoffeeList(app.getManager().findCoffee(min, max));
                coffee_list_fragment.updateUI();
            }
        }
    }

    private void findCoffeeByCorrelation() {
        var getDataFrame = new JFrame();
        String minValue = JOptionPane.showInputDialog(getDataFrame, "Enter correlation min value: ");
        String maxValue = JOptionPane.showInputDialog(getDataFrame, "Enter correlation max value: ");
        if (!Objects.equals(minValue, "") && maxValue != "") {
            float min = parseFloat(minValue);
            float max = parseFloat(maxValue);
            initCoffeeList(app.getManager().findCoffee(min, max));
            coffee_list_fragment.updateUI();
        }
    }

    /* VANS METHODS */
    private void addVan() {
        var getDataFrame = new JFrame();
        String result = JOptionPane.showInputDialog(getDataFrame, "Enter capacity: ");
        if (!Objects.equals(result, "") && result != null) {
            float capacity = parseFloat(result);
            if (capacity != 0) {
                app.getManager().addVan(new Van(capacity));
            }
        }
        initVanList();
    }

    private void changeVan() {
        var getDataFrame = new JFrame();
        String result = JOptionPane.showInputDialog(getDataFrame, "Enter capacity");
        if (!Objects.equals(result, "") && result != null) {
            float capacity = parseFloat(result);
            var vans = Repository.selectVans(selectAllVanSQL);
            if (vans != null && capacity != 0) {
                app.getManager().changeVan(vans.get(checkedVanObject).getId(), new Van(capacity));
            }
        }
        initVanList();
    }

    private void deleteVan() {
        var vans = Repository.selectVans(selectAllVanSQL);
        if (vans != null) {
            try {
                app.getManager().deleteVan(vans.get(checkedVanObject).getId());
            } catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(new JFrame(), "Cannot get that element!" +
                        "\n\tTry to refresh");
            }
        }
        initVanList();
    }

    private void loadVan() {
        var vans = Repository.selectVans(selectAllVanSQL);
        if (vans != null) {
            try {
                var id = vans.get(checkedVanObject).getId();
                Repository.deleteVanCoffeeGoods(id);
                app.getManager().loadVan(id);
            } catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(new JFrame(), "Cannot get that element!");
            }
        }
        initVanList();
    }

    private void onRefreshLists() {
        initCoffeeList(getAllCoffees());
        initVanList();
        coffeeGoodsList.removeAll();
    }

    private ArrayList<Coffee> getAllCoffees() {
        coffees = Repository.selectCoffees(selectAllCoffeeSQL);
        if (coffees != null) {
            return coffees;
        } else return new ArrayList<Coffee>();
    }

    private void initCoffeeList(ArrayList<Coffee> coffees) {
        if (coffees != null) {
            DefaultListModel<String> coffeeListModel = new DefaultListModel<>();

            if (!coffeeListFlag) {
                coffeeList.addListSelectionListener(e -> checkedCoffeeObject = coffeeList.getSelectedIndex());
                coffeeList.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent event) {
                        if (event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1) {
                            var infoFrame = new JFrame("CoffeeVan <--> info");
                            infoFrame.setContentPane(new ShowCoffeeData(coffees.get(checkedCoffeeObject)).getContentPane());
                            infoFrame.setLocationRelativeTo(null);
                            infoFrame.pack();
                            infoFrame.setVisible(true);
                        }
                        super.mouseClicked(event);
                    }
                });
                coffeeListFlag = true;
            }
            for (int i = 0; i < coffees.size(); i++) {
                coffeeListModel.add(i, i + ". " + coffees.get(i).toListView());
            }

            coffeeList.setModel(coffeeListModel);
        }
    }

    private void initVanList() {
        var vans = Repository.selectVans(selectAllVanSQL);
        if (vans != null) {
            DefaultListModel<String> vanListModel = new DefaultListModel<>();

            for (int i = 0; i < vans.size(); i++) {
                vanListModel.add(i, i + ". " + vans.get(i).toListView());
            }

            if (!vansListFlag) {
                vansList.addListSelectionListener(e -> {
                    try {
                        if (checkedVanObject != -1) {
                            vanListModel.set(checkedVanObject, vanListModel.get(checkedVanObject).replace(" ===> ", ""));
                        }
                        checkedVanObject = vansList.getSelectedIndex();
                        if (checkedVanObject != -1) {
                            vanListModel.set(checkedVanObject, vanListModel.get(checkedVanObject) + " ===> ");
                        }
                    } catch (ArrayIndexOutOfBoundsException indexOutOfBoundsException) {
                        AppLogger.logger.severe("Array index out of bounds!" + indexOutOfBoundsException.getMessage());
                    }
                });

                vansList.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent event) {
                        if (event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1) {
                            var vans = Repository.selectVans(selectAllVanSQL);
                            if (vans != null) {
                                var goods = Repository.selectCoffeeGoods(vans.get(checkedVanObject).getId());
                                initCoffeeGoodsList(goods);
                            }
                        }

                        super.mouseClicked(event);
                    }
                });
                vansListFlag = true;
            }
            vansList.setModel(vanListModel);
        }
    }

    private void initCoffeeGoodsList(ArrayList<CoffeeGoodsToListView> goods) {
        if (goods != null) {
            DefaultListModel<String> goodsListModel = new DefaultListModel<>();

            for (int i = 0; i < goods.size(); i++) {
                goodsListModel.add(i, goods.get(i).toString());
            }

            coffeeGoodsList.setModel(goodsListModel);
        }
    }

    private void openSettingFrame() {
        settingsFrame = new JFrame("CoffeeVan --- Settings");
        settingsFrame.setContentPane(new SettingsFrame().getContentPlane());
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.pack();
        settingsFrame.setVisible(true);
    }

    public static void closeAddCoffeeFrame() {
        addCoffeeFrame.dispose();
    }

    public static void closeChangeCoffeeFrame() {
        changeCoffeeFrame.dispose();
    }

    public static void closeSettingFrame() {
        settingsFrame.dispose();
    }

    public JPanel getContentPanel() {
        return main;
    }

    // flag to know if listeners were add
    private boolean coffeeListFlag = false;
    private boolean vansListFlag = false;
    private static JFrame addCoffeeFrame;
    private static JFrame changeCoffeeFrame;
    private static JFrame settingsFrame;
    private JScrollPane coffee_list_fragment;
    private JList<String> coffeeList;
    private JScrollPane vans_list_fragment;
    private JList<String> vansList;
    private JButton addCoffeeButton;
    private JButton changeCoffeeButton;
    private JButton sortByQualityButton;
    private JButton deleteCoffeeButton;
    private JButton sortByCorrelationButton;
    private JPanel main;
    private JButton refreshButton;
    private JButton findByNameButton;
    private JButton findByQualityMarkButton;
    private JButton findByNameAndStateButton;
    private JButton findByQualityMarkRangeButton;
    private JButton addVanButton;
    private JButton changeVanButton;
    private JButton deleteVanButton;
    private JButton findByCorrelationRangeButton;
    private JButton loadVanButton;
    private JList<String> coffeeGoodsList;
    private JButton settingsButton;
}
