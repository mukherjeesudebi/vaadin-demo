package com.example.application.views;

import java.util.List;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {

	private H1 viewTitle;

	public MainLayout() {
		setPrimarySection(Section.DRAWER);
		addDrawerContent();
		addHeaderContent();
	}

	private void addHeaderContent() {
		DrawerToggle toggle = new DrawerToggle();
		toggle.setAriaLabel("Menu toggle");

		Span appName = new Span("Vaadin-Demo");
		appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
		Header header = new Header(appName);

		Div leftSideComponents = new Div(toggle, header);
		leftSideComponents.addClassName("header-items");
		Div RightSideComponents = new Div(createUserProfile());
		RightSideComponents.addClassName("header-items");

		addToNavbar(true, leftSideComponents, RightSideComponents);
		setPrimarySection(Section.NAVBAR);
	}

	private MenuBar createUserProfile() {
		Div userProfile = new Div();
		userProfile.addClassName("user-profile");
		
		Div userProfileIcon = new Div();
		Avatar avatarImage = new Avatar();
		avatarImage.setImage("https://randomuser.me/api/portraits/women/76.jpg");
		userProfileIcon.add(avatarImage);
		userProfileIcon.addClassName("user-profile-icon");
		
        Div userProfileName = new Div("Test User");
        userProfileName.addClassName("user-profile-name");
        Div userProfileRole = new Div("Admin");
        userProfileRole.addClassName("user-profile-role");
        Div userProfileNameRole = new Div(userProfileName,userProfileRole);
        userProfileNameRole.addClassName("user-profile-name-role");
        
		userProfile.add(userProfileIcon, userProfileNameRole);
		
		MenuBar menuBar = new MenuBar();
		menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY,MenuBarVariant.LUMO_END_ALIGNED);
		MenuItem item = menuBar.addItem(userProfile);
        SubMenu subMenu = item.getSubMenu();
        subMenu.addItem("Profile");
        subMenu.addItem("Account");
        subMenu.addItem("Preferences");
        subMenu.add(new Hr());
        subMenu.addItem("Sign out");
        
		return menuBar;
	}

	private void addDrawerContent() {

		Scroller scroller = new Scroller(createNavigation());

		addToDrawer(scroller, createFooter());
	}

	private SideNav createNavigation() {
		SideNav nav = new SideNav();

		List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();
		menuEntries.forEach(entry -> {
			if (entry.icon() != null) {
				nav.addItem(new SideNavItem(entry.title(), entry.path(), new SvgIcon(entry.icon())));
			} else {
				nav.addItem(new SideNavItem(entry.title(), entry.path()));
			}
		});

		return nav;
	}

	private Footer createFooter() {
		Footer layout = new Footer();

		return layout;
	}

	@Override
	protected void afterNavigation() {
		super.afterNavigation();
		// viewTitle.setText(getCurrentPageTitle());
	}

	private String getCurrentPageTitle() {
		return MenuConfiguration.getPageHeader(getContent()).orElse("");
	}
}
