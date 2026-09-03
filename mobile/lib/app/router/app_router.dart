import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:fleetcommand_mobile/features/admin/admin_screen.dart';
import 'package:fleetcommand_mobile/features/driver/driver_screen.dart';
import 'package:fleetcommand_mobile/features/hirer/hirer_screen.dart';
import 'package:fleetcommand_mobile/features/owner/owner_screen.dart';

final GoRouter appRouter = GoRouter(
  initialLocation: '/hirer',
  routes: [
    GoRoute(
      path: '/hirer',
      builder: (context, state) => const HirerScreen(),
    ),
    GoRoute(
      path: '/owner',
      builder: (context, state) => const OwnerScreen(),
    ),
    GoRoute(
      path: '/driver',
      builder: (context, state) => const DriverScreen(),
    ),
    GoRoute(
      path: '/admin',
      builder: (context, state) => const AdminScreen(),
    ),
  ],
);
