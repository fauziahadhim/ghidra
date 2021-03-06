/* ###
 * IP: GHIDRA
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
package ghidra.framework.main.projectdata.actions;

import java.awt.Component;

import javax.swing.Icon;

import docking.action.MenuData;
import docking.action.ToolBarData;
import ghidra.framework.main.datatable.ProjectDataTreeContextAction;
import ghidra.framework.main.datatree.FindCheckoutsDialog;
import ghidra.framework.main.datatree.ProjectDataTreeActionContext;
import ghidra.framework.model.DomainFolder;
import ghidra.framework.plugintool.Plugin;
import ghidra.util.HelpLocation;
import resources.MultiIcon;
import resources.ResourceManager;

public class FindCheckoutsAction extends ProjectDataTreeContextAction {

	private Plugin plugin;

	public FindCheckoutsAction(String owner, Plugin plugin) {
		super("Find Checkouts", owner);
		this.plugin = plugin;
		String group = "Repository";
		Icon searchIcon = ResourceManager.loadImage("images/magnifier.png");
		Icon smallCheckIcon = ResourceManager.loadImage("images/check.png");
		MultiIcon icon = new MultiIcon(searchIcon);
		icon.addIcon(smallCheckIcon);
		setToolBarData(new ToolBarData(icon, group));
		setPopupMenuData(new MenuData(new String[] { "Find Checkouts..." }, icon, "Repository"));
		setDescription("Find my checkouts recursively");
		setHelpLocation(new HelpLocation("VersionControl", "Find_Checkouts"));
		setEnabled(false);
	}

	@Override
	protected void actionPerformed(ProjectDataTreeActionContext context) {
		DomainFolder domainFolder = context.getSelectedFolders().get(0);
		findCheckouts(domainFolder, context.getComponent());
	}

	@Override
	protected boolean isEnabledForContext(ProjectDataTreeActionContext context) {
		if (context.isReadOnlyProject()) {
			return false;
		}
		return context.getFolderCount() == 1;
	}

	private void findCheckouts(DomainFolder folder, Component comp) {

		FindCheckoutsDialog dialog = new FindCheckoutsDialog(plugin, folder);
		plugin.getTool().showDialog(dialog, comp);
	}

}
