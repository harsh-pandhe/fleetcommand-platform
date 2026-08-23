import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

abstract final class AppRoutes {
  static const hirer = '/hirer';
  static const owner = '/owner';
  static const driver = '/driver';
  static const admin = '/admin';
}

final GoRouter appRouter = GoRouter(
  initialLocation: AppRoutes.hirer,
  routes: <RouteBase>[
    GoRoute(
      path: AppRoutes.hirer,
      builder: (BuildContext context, GoRouterState state) =>
          const RoleRootPlaceholderScreen(role: 'Hirer'),
    ),
    GoRoute(
      path: AppRoutes.owner,
      builder: (BuildContext context, GoRouterState state) =>
          const RoleRootPlaceholderScreen(role: 'Owner'),
    ),
    GoRoute(
      path: AppRoutes.driver,
      builder: (BuildContext context, GoRouterState state) =>
          const RoleRootPlaceholderScreen(role: 'Driver'),
    ),
    GoRoute(
      path: AppRoutes.admin,
      builder: (BuildContext context, GoRouterState state) =>
          const RoleRootPlaceholderScreen(role: 'Admin'),
    ),
  ],
);

class RoleRootPlaceholderScreen extends StatelessWidget {
  const RoleRootPlaceholderScreen({super.key, required this.role});

  final String role;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('FleetCommand')),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text('$role area', style: Theme.of(context).textTheme.headlineMedium),
            const SizedBox(height: 8),
            const Text('Placeholder role-root screen'),
            const SizedBox(height: 24),
            Wrap(
              spacing: 8,
              runSpacing: 8,
              alignment: WrapAlignment.center,
              children: <Widget>[
                _RoleNavigationButton(label: 'Hirer', path: AppRoutes.hirer),
                _RoleNavigationButton(label: 'Owner', path: AppRoutes.owner),
                _RoleNavigationButton(label: 'Driver', path: AppRoutes.driver),
                _RoleNavigationButton(label: 'Admin', path: AppRoutes.admin),
              ],
            ),
          ],
        ),
      ),
    );
  }
}

class _RoleNavigationButton extends StatelessWidget {
  const _RoleNavigationButton({required this.label, required this.path});

  final String label;
  final String path;

  @override
  Widget build(BuildContext context) {
    return OutlinedButton(
      key: Key('role-nav-${label.toLowerCase()}'),
      onPressed: () => context.go(path),
      child: Text(label),
    );
  }
}
