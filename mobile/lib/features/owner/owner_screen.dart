import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class OwnerScreen extends StatelessWidget {
  const OwnerScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Owner Screen'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'OWNER ROOT',
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
              onPressed: () => context.go('/driver'),
              child: const Text('Go to Driver'),
            ),
            const SizedBox(height: 8),
            ElevatedButton(
              onPressed: () => context.go('/admin'),
              child: const Text('Go to Admin'),
            ),
          ],
        ),
      ),
    );
  }
}
