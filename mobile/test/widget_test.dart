import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:fleetcommand_mobile/app/app.dart';
import 'package:fleetcommand_mobile/features/hirer/hirer_screen.dart';
import 'package:fleetcommand_mobile/features/owner/owner_screen.dart';

void main() {
  testWidgets('App shell can be constructed and contains ProviderScope', (WidgetTester tester) async {
    await tester.pumpWidget(
      const ProviderScope(
        child: App(),
      ),
    );
    await tester.pumpAndSettle();

    // Verify initial screen is Hirer Screen
    expect(find.byType(HirerScreen), findsOneWidget);
    expect(find.text('HIRER ROOT'), findsOneWidget);
  });

  testWidgets('Navigation between placeholder role screens works', (WidgetTester tester) async {
    await tester.pumpWidget(
      const ProviderScope(
        child: App(),
      ),
    );
    await tester.pumpAndSettle();

    // Find and tap navigation button to Owner
    final goOwnerButton = find.widgetWithText(ElevatedButton, 'Go to Owner');
    expect(goOwnerButton, findsOneWidget);
    await tester.tap(goOwnerButton);
    await tester.pumpAndSettle();

    // Verify Owner Screen is resolved
    expect(find.byType(OwnerScreen), findsOneWidget);
    expect(find.text('OWNER ROOT'), findsOneWidget);
  });
}
