/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.panels;

import java.sql.SQLException;

/**
 *
 * @author Lorenzo
 */
public interface Loadable {
    public void load() throws SQLException;
}
