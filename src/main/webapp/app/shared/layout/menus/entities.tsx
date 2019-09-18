import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <MenuItem icon="asterisk" to="/entity/client">
      Client
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/history">
      History
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/address">
      Address
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/product">
      Product
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/product-history">
      Product History
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
