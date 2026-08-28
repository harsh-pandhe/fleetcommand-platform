import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class AdminScreen extends StatelessWidget {
  const AdminScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Admin Screen'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'ADMIN ROOT',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            const Text('Placeholder screen'),
            const SizedBox(height: 24),
            ElevatedButton(
              onPressed: () => context.go('/hirer'),
              child: const Text('Go to Hirer'),
            ),
            const SizedBox(height: 8),
            ElevatedButton(
              onPressed: () => context.go('/owner'),
              child: const Text('Go to Owner'),
            ),
            const SizedBox(height: 8),
            ElevatedButton(
              onPressed: () => context.go('/driver'),
              child: const Text('Go to Driver'),
            ),
          ],
        ),
      ),
    );
  }
}
